import 'package:flutter/material.dart';
import 'listvisitedscreen.dart';
import 'listunvisitedscreen.dart';
import 'listlocationsscreen.dart';

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Home Screen'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => ListUnvisitedScreen()),
                );
              },
              child: Text('Unvisited'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => ListLocationScreen()),
                );
              },
              child: Text('Locations'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => ListVisitedLocationScreen()),
                );
              },
              child: Text('Visited'),
            ),
          ],
        ),
      ),
    );
  }
}
