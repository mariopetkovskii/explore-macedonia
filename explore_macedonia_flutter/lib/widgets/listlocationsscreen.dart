import 'dart:convert';
import 'package:explore_macedonia_flutter/widgets/displaytoken.dart';
import 'package:explore_macedonia_flutter/widgets/locationroutescreen.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;


class ListLocationScreen extends StatefulWidget {
  @override
  _ListLocationScreenState createState() => _ListLocationScreenState();
}

class _ListLocationScreenState extends State<ListLocationScreen> {
  List<Location> _locations = [];

  @override
  void initState() {
    super.initState();
    _fetchLocations();
  }

  Future<void> _fetchLocations() async {
    final url = Uri.parse('http://10.0.2.2:8080/rest/location/getAll');
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final jsonBody = jsonDecode(response.body);
      final locations = (jsonBody as List).map((e) => Location.fromJson(e)).toList();
      setState(() {
        _locations = locations;
      });
    } else {
      throw Exception('Failed to fetch locations');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Locations'),
      ),
      body: _locations.isEmpty
          ? Center(child: CircularProgressIndicator())
          : ListView.builder(
              itemCount: _locations.length,
              itemBuilder: (context, index) {
                final location = _locations[index];
                return ListTile(
                  title: Text(location.location),
                  subtitle: Text(location.description),
                  trailing: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      if (location.isRecommended)
                        Text('Recommended: Yes')
                      else
                        Text('Recommended: No'),
                      ElevatedButton(
                        onPressed: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => RouteWidget(
                                destinationLatitude: location.latitude,
                                destinationLongitude: location.longitude,
                              ),
                            ),
                          );
                        },
                        child: Text('Visit'),
                      ),
                    ],
                  ),
                );
              },
            ),
    );
  }
}

class Location {
  final int id;
  final String location;
  final double longitude;
  final double latitude;
  final String description;
  final bool isRecommended;

  Location({
    required this.id,
    required this.location,
    required this.longitude,
    required this.latitude,
    required this.description,
    required this.isRecommended,
  });

  factory Location.fromJson(Map<String, dynamic> json) {
    return Location(
      id: json['id'],
      location: json['location'],
      longitude: json['longitude'],
      latitude: json['latitude'],
      description: json['description'],
      isRecommended: json['isRecommended'],
    );
  }
}
