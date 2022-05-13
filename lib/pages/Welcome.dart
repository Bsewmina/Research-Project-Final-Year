import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import '../misc/colors.dart';
import '../widgets/app_text.dart';
import '../widgets/app_text_large.dart';
import '../widgets/responsive_button.dart';

class Welcome extends StatefulWidget {
  const Welcome({Key? key}) : super(key: key);

  @override
  State<Welcome> createState() => _WelcomeState();
}

class _WelcomeState extends State<Welcome> {
  List images =[
    "welcome-one.png",
    "welcome-two.png",
    "welcome-three.png",
  ];
  List mainText =[
    "Bus Rides",
    "Train Rides",
    "Combined Rides",
  ];
  List subText =[
    "Track your Bus",
    "Track your Train",
    "Track your Bus/Train",
  ];
  List description =[
    "We provide full fledged online bus tracking and scheduling platform to track real-time location of buses and get latest bus schedule data.",
    "We provide full fledged online bus tracking and scheduling platform to track real-time location of buses and get latest bus schedule data.",
    "We provide full fledged online bus tracking and scheduling platform to track real-time location of buses and get latest bus schedule data.",
  ];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: PageView.builder(
          scrollDirection: Axis.vertical, //vertical slider
          itemCount: images.length, //number of items in the vertical slider is equal to the images declared in the 'images' list
          itemBuilder: (_,index){ //slider widget
            return Container(
              width: double.maxFinite,
              height: double.maxFinite,
              decoration: BoxDecoration(
                image: DecorationImage(
                  image: AssetImage(
                    "img/"+images[index]
                  ),
                  fit: BoxFit.cover
                )
              ),
              child: Container(
                margin: const EdgeInsets.only(top:150, left:20, right: 20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        AppLargeText(text: mainText[index]),
                        AppText(text: subText[index], size: 30),
                        SizedBox(height: 20),
                        Container(
                          width: 250,
                          child: AppText(
                            text: description[index],
                            color: AppColors.textColor2,
                            size: 14,
                          ),
                        ),
                        SizedBox(height: 40),
                        ResponsiveButton(width: 120,)

                      ],
                    ),//Col-1
                    Column(
                      children: List.generate(3, (indexDots){
                        return Container(
                          margin: const EdgeInsets.only(bottom: 2) ,
                          width: 8,
                          height: index==indexDots?25:8,
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(8),
                            color: index==indexDots?AppColors.mainColor:AppColors.mainColor.withOpacity(0.3)
                          ),
                        );
                      }),
                    ),
                  ],
                ),
              ) ,
            );
    }),
    );
  }
}
