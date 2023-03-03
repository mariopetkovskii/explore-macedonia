import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';
import 'package:location/location.dart';

class LocationDetailsScreen extends StatefulWidget {
  final double latitude;
  final double longitude;

  const LocationDetailsScreen({Key? key, required this.latitude, required this.longitude}) : super(key: key);

  @override
  _LocationDetailsScreenState createState() => _LocationDetailsScreenState();
}

class _LocationDetailsScreenState extends State<LocationDetailsScreen> {
  final MapController _mapController = MapController();
  LocationData? _currentLocation;
  List<Marker> _markers = [];

  @override
  void initState() {
    super.initState();
    _getCurrentLocation();
  }

  Future<void> _getCurrentLocation() async {
    final location = Location();
    try {
      final currentLocation = await location.getLocation();
      setState(() {
        _currentLocation = currentLocation;
        _markers = [
          Marker(
            width: 80.0,
            height: 80.0,
            point: LatLng(widget.latitude, widget.longitude),
            builder: (ctx) => Icon(Icons.location_on, color: Colors.red),
          ),
        ];
      });
    } catch (e) {
      print('Failed to get current location: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Location Details'),
      ),
      body: _currentLocation == null
          ? Center(child: CircularProgressIndicator())
          : FlutterMap(
              mapController: _mapController,
              options: MapOptions(
                center: LatLng(_currentLocation!.latitude!, _currentLocation!.longitude!),
                zoom: 12.0,
              ),              
            ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          _mapController.move(LatLng(widget.latitude, widget.longitude), 12.0);
        },
        child: Icon(Icons.navigation),
      ),
    );
  }
}
