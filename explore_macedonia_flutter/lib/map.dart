import 'package:flutter/material.dart';
import 'package:flutter_polyline_points/flutter_polyline_points.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'dart:math' show cos, sqrt, asin;
import 'package:http/http.dart' as http;
import 'dart:convert';

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
                  points: _info.polylinePoints,
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
  static const String apiKey = 'AIzaSyDrHM0FgOjpeVYjFiBdXL-u9EZAciaCcsg'; // Replace with your own API key

  static Future<Directions?> getDirections(LatLng origin, LatLng destination) async {
    final url =
        'https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${destination.latitude},${destination.longitude}&key=$apiKey';

    final response = await http.get(Uri.parse(url));

    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      final routes = json['routes'];

     if (routes != null && routes.isNotEmpty) {
        final route = routes[0];
        final bounds = route['bounds'];
        final legs = route['legs'];

        if (bounds != null && legs != null && legs.isNotEmpty) {
          final northeast = bounds['northeast'];
          final southwest = bounds['southwest'];

          final boundsLatLng = LatLngBounds(
            northeast: LatLng(northeast['lat'], northeast['lng']),
            southwest: LatLng(southwest['lat'], southwest['lng']),
          );

          final polylinePoints = PolylinePoints()
              .decodePolyline(route['overview_polyline']['points'])
              .map((point) => LatLng(point.latitude, point.longitude))
              .toList();

          final totalDistance = legs[0]['distance']['text'];
          final totalDuration = legs[0]['duration']['text'];

          return Directions(boundsLatLng, polylinePoints, totalDistance, totalDuration);
        }
      }
    }

    return null;
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
