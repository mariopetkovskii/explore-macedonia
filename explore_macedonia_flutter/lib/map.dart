import 'package:flutter/material.dart';
import 'package:flutter_polyline_points/flutter_polyline_points.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'dart:math' show cos, sqrt, asin;

class MapScreen extends StatefulWidget {
  final LatLng destination;

  MapScreen({required this.destination});

  @override
  _MapScreenState createState() => _MapScreenState();
}

class _MapScreenState extends State<MapScreen> {
  static const _initialCameraPosition = CameraPosition(
    target: LatLng(42.131178, 21.725486),
    zoom: 11.5,
  );

  late GoogleMapController _googleMapController;
  late Marker _origin;
  late Marker _destination;
  late Directions _info = Directions(
    LatLngBounds(northeast: LatLng(0, 0), southwest: LatLng(0, 0)),
    [],
    '',
    '',
  );

  @override
  void initState() {
    super.initState();
    _origin = Marker(
      markerId: const MarkerId('origin'),
      infoWindow: const InfoWindow(title: 'Origin'),
      icon: BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueGreen),
      position: LatLng(0, 0), // Placeholder for current location
    );
    _destination = Marker(
      markerId: const MarkerId('destination'),
      infoWindow: const InfoWindow(title: 'Destination'),
      icon: BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueBlue),
      position: widget.destination,
    );
    _setMarkers();
    _setPolyline();
  }

  @override
  void dispose() {
    _googleMapController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: false,
        title: const Text('Google Maps'),
        actions: [
          if (_origin != null)
            TextButton(
              onPressed: () => _googleMapController.animateCamera(
                CameraUpdate.newCameraPosition(
                  CameraPosition(
                    target: _origin.position,
                    zoom: 14.5,
                    tilt: 50.0,
                  ),
                ),
              ),
              style: TextButton.styleFrom(
                primary: Colors.green,
                textStyle: const TextStyle(fontWeight: FontWeight.w600),
              ),
              child: const Text('ORIGIN'),
            ),
          if (_destination != null)
            TextButton(
              onPressed: () => _googleMapController.animateCamera(
                CameraUpdate.newCameraPosition(
                  CameraPosition(
                    target: _destination.position,
                    zoom: 14.5,
                    tilt: 50.0,
                  ),
                ),
              ),
              style: TextButton.styleFrom(
                primary: Colors.blue,
                textStyle: const TextStyle(fontWeight: FontWeight.w600),
              ),
              child: const Text('DEST'),
            )
        ],
      ),
      body: Stack(
        alignment: Alignment.center,
        children: [
          GoogleMap(
            myLocationButtonEnabled: false,
            zoomControlsEnabled: false,
            initialCameraPosition: _initialCameraPosition,
            onMapCreated: (controller) => _googleMapController = controller,
            markers: {
              if (_origin != null) _origin,
              if (_destination != null) _destination
            },
            polylines: {
              if (_info.polylinePoints.isNotEmpty)
                Polyline(
                  polylineId: const PolylineId('overview_polyline'),
                  color: Colors.red,
                  width: 5,
                  points: _info.polylinePoints
                      .map((e) => LatLng(e.latitude, e.longitude))
                      .toList(),
                ),
            },
            onLongPress: _addMarker,
          ),
          if (_info.totalDistance.isNotEmpty && _info.totalDuration.isNotEmpty)
            Positioned(
              top: 20.0,
              child: Container(
                padding: const EdgeInsets.symmetric(
                  vertical: 6.0,
                  horizontal: 12.0,
                ),
                decoration: BoxDecoration(
                  color: Colors.yellowAccent,
                  borderRadius: BorderRadius.circular(20.0),
                  boxShadow: const [
                    BoxShadow(
                      color: Colors.black26,
                      offset: Offset(0, 2),
                      blurRadius: 6.0,
                    )
                  ],
                ),
                child: Text(
                  '${_info.totalDistance}, ${_info.totalDuration}',
                  style: const TextStyle(
                    fontSize: 18.0,
                    fontWeight: FontWeight.w600,
                  ),
                ),
              ),
            ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: Theme.of(context).primaryColor,
        foregroundColor: Colors.black,
        onPressed: () => _googleMapController.animateCamera(
          _info.bounds != null
              ? CameraUpdate.newLatLngBounds(_info.bounds, 100.0)
              : CameraUpdate.newCameraPosition(_initialCameraPosition),
        ),
        child: const Icon(Icons.center_focus_strong),
      ),
    );
  }

  void _addMarker(LatLng pos) async {
    setState(() {
      _origin = Marker(
        markerId: const MarkerId('origin'),
        infoWindow: const InfoWindow(title: 'Origin'),
        icon: BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueGreen),
        position: pos,
      );
    });

    Directions? directions = await DirectionsRepository.getDirections(
      _origin.position,
      widget.destination,
    );
    if (directions != null) {
      setState(() {
        _info = directions;
      });
    }
  }

  void _setMarkers() async {
    LatLng currentLocation = await _getCurrentLocation();

    setState(() {
      _origin = Marker(
        markerId: const MarkerId('origin'),
        infoWindow: const InfoWindow(title: 'Origin'),
        icon: BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueGreen),
        position: currentLocation,
      );

      _destination = Marker(
        markerId: const MarkerId('destination'),
        infoWindow: const InfoWindow(title: 'Destination'),
        icon: BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueBlue),
        position: widget.destination,
      );
    });
  }

  Future<LatLng> _getCurrentLocation() {
    // Implement your logic to get the current location using geolocation package or other methods
    // Return a Future with the LatLng representing the current location
    return Future.value(LatLng(42.131178, 21.725486)); // Placeholder for current location
  }

  void _setPolyline() async {
    Directions? directions = await DirectionsRepository.getDirections(
      _origin.position,
      widget.destination,
    );
    if (directions != null) {
      setState(() {
        _info = directions;
      });
    }
  }
}

class DirectionsRepository {
  static Future<Directions?> getDirections(LatLng origin, LatLng destination) {
    // Implement your logic to fetch directions and return a Directions object
    // You can use the origin and destination coordinates to make API requests or perform calculations
    return Future.delayed(const Duration(seconds: 1), () {
      // Placeholder implementation
      final polylinePoints = [
        LatLng(42.131178, 21.725486),
        LatLng(42.100, 21.700),
        destination,
      ];

      final totalDistance = _calculateTotalDistance(polylinePoints);
      final totalDuration = '1h 30m';

      return Directions(
        LatLngBounds(northeast: LatLng(0, 0), southwest: LatLng(0, 0)),
        polylinePoints,
        totalDistance,
        totalDuration,
      );
    });
  }

  static String _calculateTotalDistance(List<LatLng> polylinePoints) {
    // Implement your logic to calculate the total distance of the polyline
    // You can use the polylinePoints list to iterate and calculate the distance between each pair of points
    // Return the total distance as a string
    return '10.5 km';
  }
}

class Directions {
  final LatLngBounds bounds;
  final List<LatLng> polylinePoints;
  final String totalDistance;
  final String totalDuration;

  Directions(
    this.bounds,
    this.polylinePoints,
    this.totalDistance,
    this.totalDuration,
  );
}