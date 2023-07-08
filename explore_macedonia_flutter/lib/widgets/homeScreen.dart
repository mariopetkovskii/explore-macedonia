import 'package:flutter/material.dart';
import 'listvisitedscreen.dart';
import 'listunvisitedscreen.dart';
import 'listlocationsscreen.dart';
import 'compassScreen.dart';

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
            Image.asset(
              'assets/mapmacedonia.jpg',
              width: 200,
              height: 200,
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => ListUnvisitedScreen()),
                );
              },
              style: ElevatedButton.styleFrom(
                primary: Colors.blue,
              ),
              child: Text('Unvisited'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => ListLocationScreen()),
                );
              },
              style: ElevatedButton.styleFrom(
                primary: Colors.pink,
              ),
              child: Text('Locations'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => ListVisitedLocationScreen()),
                );
              },
              style: ElevatedButton.styleFrom(
                primary: Colors.lightGreen,
              ),
              child: Text('Visited'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => CompassHome()),
                );
              },
              child: Text('Compass'),
            ),
          ],
        ),
      ),
    );
  }
}
