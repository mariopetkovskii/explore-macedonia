import 'dart:io';
import 'package:explore_macedonia_flutter/widgets/ListUnvisitedScreen.dart';
import 'package:explore_macedonia_flutter/widgets/listlocationsnotauth.dart';
import 'package:explore_macedonia_flutter/widgets/displaytoken.dart';
import 'package:explore_macedonia_flutter/widgets/listlocationsscreen.dart';
import 'package:explore_macedonia_flutter/widgets/listvisitedscreen.dart';
import 'package:flutter/widgets.dart';
import 'package:explore_macedonia_flutter/widgets/loginscreen.dart';
import 'package:explore_macedonia_flutter/widgets/registerscreen.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

void main() {
  runApp(
    MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MediaQuery(
        data: MediaQueryData(),
        child: MyHomePage(title: 'Flutter Demo Home Page'),
      ),
    ),
  );
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  bool _isLoggedIn = false;

  @override
  void initState() {
    super.initState();
    _checkLoginStatus();
  }

  Future<void> _checkLoginStatus() async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString('token');
    setState(() {
      _isLoggedIn = token != null;
    });
  }

  void _logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('token');
    setState(() {
      _isLoggedIn = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
        actions: _isLoggedIn
            ? [
                IconButton(
                  onPressed: _logout,
                  icon: Icon(Icons.logout),
                )
              ]
            : [
                IconButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => LoginScreen()),
                    );
                  },
                  icon: Icon(Icons.login),
                ),
              ],
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Image.asset(
              'assets/mapmacedonia.jpg',
              height: 200,
              fit: BoxFit.cover,
            ),
            SizedBox(height: 20.0),
            if (_isLoggedIn)
              Column(
                children: [
                  ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => ListLocationScreenNon()),
                      );
                    },
                    child: Text(
                      'All locations',
                      style: TextStyle(fontSize: 20),
                    ),
                    style: ElevatedButton.styleFrom(
                      primary: Colors.blueAccent,
                      minimumSize: Size(200, 50),
                    ),
                  ),
                  // SizedBox(height: 20.0),
                  // ElevatedButton(
                  //   onPressed: () {
                  //     Navigator.push(
                  //       context,
                  //       MaterialPageRoute(builder: (context) => ListUnvisitedScreen()),
                  //     );
                  //   },
                  //   child: Text(
                  //     'Unvisited locations',
                  //     style: TextStyle(fontSize: 20),
                  //   ),
                  //   style: ElevatedButton.styleFrom(
                  //     primary: Colors.purpleAccent,
                  //     minimumSize: Size(200, 50),
                  //   ),
                  // ),
                  // SizedBox(height: 20.0),
                  // ElevatedButton(
                  //   onPressed: () {
                  //     Navigator.push(
                  //       context,
                  //       MaterialPageRoute(builder: (context) => ListVisitedLocationScreen()),
                  //     );
                  //   },
                  //   child: Text(
                  //     'Visited locations',
                  //     style: TextStyle(fontSize: 20),
                  //   ),
                  //   style: ElevatedButton.styleFrom(
                  //     primary: Colors.greenAccent,
                  //     minimumSize: Size(200, 50),
                  //   ),
                  // ),
                ],
              )
            else
              Column(
                children: [
                  ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => LoginScreen()),
                      );
                    },
                    child: Text(
                      'Login',
                      style: TextStyle(fontSize: 20),
                    ),
                    style: ElevatedButton.styleFrom(
                      primary: Colors.green,
                      minimumSize: Size(200, 50),
                    ),
                  ),
                  SizedBox(height: 20.0),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => RegisterScreen()),
                      );
                    },
                    child: Text(
                      'Register',
                      style: TextStyle(fontSize: 20),
                    ),
                    style: ElevatedButton.styleFrom(
                      primary: Colors.orange,
                      minimumSize: Size(200, 50),
                    ),
                  ),
                ],
              ),
          ],
        ),
      ),
    );
  }
}
