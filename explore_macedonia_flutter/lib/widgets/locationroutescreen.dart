import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

class RouteWidget extends StatefulWidget {
  final double destinationLatitude;
  final double destinationLongitude;

  RouteWidget({required this.destinationLatitude, required this.destinationLongitude});

  @override
  _RouteWidgetState createState() => _RouteWidgetState();
}

class _RouteWidgetState extends State<RouteWidget> {
  late GoogleMapController _controller;
  Set<Marker> _markers = {};
  Set<Polyline> _polylines = {};

  @override
  void initState() {
    super.initState();
    _addMarker();
    _getPolylines();
  }

  @override
  Widget build(BuildContext context) {
    return GoogleMap(
      initialCameraPosition: CameraPosition(
        target: LatLng(widget.destinationLatitude, widget.destinationLongitude),
        zoom: 14.0,
      ),
      onMapCreated: (GoogleMapController controller) {
        _controller = controller;
      },
      markers: _markers,
      polylines: _polylines,
    );
  }

  void _addMarker() {
    setState(() {
      _markers.add(Marker(
        markerId: MarkerId('Destination'),
        position: LatLng(widget.destinationLatitude, widget.destinationLongitude),
        infoWindow: InfoWindow(
          title: 'Destination',
        ),
      ));
    });
  }

  void _getPolylines() async {
    // TODO: Add code to get the polylines from the Google Maps Directions API based on the destination latitude and longitude.
    // Once you have the polylines, add them to the _polylines set using setState().
  }
}
