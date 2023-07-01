import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';


class ListVisitedLocationScreen extends StatefulWidget {
  @override
  _ListVisitedLocationScreenState createState() => _ListVisitedLocationScreenState();
}

class _ListVisitedLocationScreenState extends State<ListVisitedLocationScreen> {
  List<VisitedLocation> _locations = [];

  @override
  void initState() {
    super.initState();
    _fetchLocations();
  }

  Future<void> _fetchLocations() async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString('token');
    final url = Uri.parse('http://10.0.2.2:8080/rest/location/visitLocation');
    final response = await http.get(url, headers: {'Authorization': 'Bearer $token'},);

    if (response.statusCode == 200) {
      final jsonBody = jsonDecode(response.body);
      final visitedlocations = (jsonBody as List).map((e) => VisitedLocation.fromJson(e)).toList();
      setState(() {
        _locations = visitedlocations;
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
                    ],
                  ),
                );
              },
            ),
    );
  }
}

class VisitedLocation {
  final int id;
  final String location;
  final double longitude;
  final double latitude;
  final String description;
  final bool isRecommended;

  VisitedLocation({
    required this.id,
    required this.location,
    required this.longitude,
    required this.latitude,
    required this.description,
    required this.isRecommended,
  });

  factory VisitedLocation.fromJson(Map<String, dynamic> json) {
    return VisitedLocation(
      id: json['id'],
      location: json['location'],
      longitude: json['longitude'],
      latitude: json['latitude'],
      description: json['description'],
      isRecommended: json['isRecommended'],
    );
  }
}
