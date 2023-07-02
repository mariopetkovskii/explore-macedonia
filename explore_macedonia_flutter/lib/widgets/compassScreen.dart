import 'package:flutter/material.dart';
import 'package:smooth_compass/utils/src/compass_ui.dart';

class CompassHome extends StatelessWidget{
  const CompassHome({Key?key}) : super(key: key);

  Widget build(BuildContext context){
    return Scaffold(
      appBar: AppBar(
        title: const Text("compass"),
      ),
      body: SmoothCompass(
        compassBuilder: (context, snapshot, child){
          return Center(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                AnimatedRotation(turns: snapshot?.data?.turns??0, 
                duration: const Duration(milliseconds: 400),
                child: Container(
                  height: 200,
                  width: 200,
                  decoration: const BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/compass.png"),
                      fit: BoxFit.fill
                    ),
                   ),
                ),
                ),
                Text("${snapshot?.data?.angle.toStringAsFixed(2)??0}")
              ],
            ),
          );
        },
      ),
    );
  }
}