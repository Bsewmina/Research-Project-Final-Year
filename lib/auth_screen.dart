import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter_auth_animation/constants.dart';
import 'package:flutter_auth_animation/widgets/login_form.dart';
import 'package:flutter_auth_animation/widgets/sign_up_form.dart';
import 'package:flutter_auth_animation/widgets/socal_buttons.dart';
import 'package:flutter_svg/flutter_svg.dart';

class AuthScreen extends StatefulWidget {
  const AuthScreen({Key? key}) : super(key: key);

  @override
  _AuthScreenState createState() => _AuthScreenState();
}
class _AuthScreenState extends State<AuthScreen>
    with SingleTickerProviderStateMixin {

  bool _isShowSignUp = false ;

  late AnimationController _animationController;
  late Animation<double> _animationTextRotate;

  void setupAnimation(){
    _animationController = AnimationController(vsync: this, duration: defaultDuration);
    _animationTextRotate = Tween<double>(begin: 0, end: 90).animate(_animationController);

  }
  void updateView() {
    setState(() {
      _isShowSignUp = !_isShowSignUp;
    });
    _isShowSignUp ? _animationController.forward() : _animationController.reverse();
  }

  @override
  void initState(){
    setupAnimation();
    super.initState();
  }
  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    final _size = MediaQuery.of(context).size;
    return Scaffold(
      body: AnimatedBuilder(
          animation: _animationController,
          builder: (context, _) {
            return Stack(
              children: [
                //Login
                AnimatedPositioned(
                  duration: defaultDuration,
                  width: _size.width *0.88,
                  height: _size.height,
                  left: _isShowSignUp ? -_size.width * 0.76 : 0,
                  child: Container(
                    color: login_bg,
                    child: LoginForm(),
                  ),
                ),

                //Sign Up
                AnimatedPositioned(
                    duration: defaultDuration,
                    height: _size.height,
                    width: _size.width * 0.88,
                    left: _isShowSignUp ? _size.width * 0.12 : _size.width * 0.88,
                    child: Container(
                      color: signup_bg,
                      child: SignUpForm(),


                    )),

                //Logo
                AnimatedPositioned(
                  duration: defaultDuration,
                  right: _isShowSignUp ? - _size.width * 0.10 : _size.width * 0.10 ,
                  left: 0,
                  top: _size.height * 0.1 ,
                  child: CircleAvatar(
                    radius: 20,
                    backgroundColor: Colors.white60,
                    child: AnimatedSwitcher(
                      duration: defaultDuration,
                      child: _isShowSignUp
                          ? SvgPicture.asset(
                          "assets/logo tl.svg",
                          color: signup_bg
                      )
                          : SvgPicture.asset(
                          "assets/logo tl.svg",
                          color: login_bg
                      ),
                    ),
                  ),
                ),
                AnimatedPositioned(
                  duration: defaultDuration,
                  bottom: _size.height * 0.1,
                  right: _isShowSignUp ? - _size.width * 0.06 : _size.width * 0.06 ,
                  width: _size.width,
                  child: SocalButtns(
                  ),
                ),

                //Login Text

                AnimatedPositioned(
                  duration: defaultDuration,
                  bottom: _isShowSignUp ?  _size.height /2 -80
                      :_size.height * 0.3,
                  left: _isShowSignUp ? 0 : _size.width * 0.44 - 80,
                  child: AnimatedDefaultTextStyle(
                    duration: defaultDuration,
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: _isShowSignUp ? 20 : 32,
                      fontWeight: FontWeight.bold,
                      color: _isShowSignUp ? Colors.white : Colors.white70,
                    ),
                    child: Transform.rotate(
                      angle:  -_animationTextRotate.value * pi / 180 ,
                      alignment: Alignment.topLeft,
                      child: InkWell(
                        onTap: (){
                          if (_isShowSignUp) {
                            updateView();
                          } else {
                            //login
                          }
                        },
                        child: Container(
                          padding: const EdgeInsets.symmetric(
                              vertical: defpaultPadding * 0.75),
                          // color: Colors.red,
                          width: 160,
                          child: Text("Log in" . toUpperCase(),
                          ),
                        ),
                      ),
                    ),
                  ),
                ),

                //Sign up Text
                AnimatedPositioned(
                  duration: defaultDuration,
                  bottom: !_isShowSignUp
                      ?  _size.height /2 -80
                      :_size.height * 0.3,
                  right: _isShowSignUp ? _size.width * 0.44 - 80 : 0 ,
                  child: AnimatedDefaultTextStyle(
                    duration: defaultDuration,
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: !_isShowSignUp ? 20 : 32,
                      fontWeight: FontWeight.bold,
                      color: _isShowSignUp ? Colors.white : Colors.white70,
                    ),
                    child: Transform.rotate(
                      angle:  (90-_animationTextRotate.value) * pi / 180 ,
                      alignment: Alignment.topRight,
                      child: InkWell(
                        onTap: (){
                          if (_isShowSignUp) {
                            //Sign up
                          } else {
                            updateView();
                          }
                        },
                        child: Container(
                          padding: const EdgeInsets.symmetric(
                              vertical: defpaultPadding * 0.75),
                          // color: Colors.red,
                          width: 160,
                          child: Text("Sign Up" . toUpperCase(),
                          ),
                        ),
                      ),
                    ),
                  ),
                ),
              ],
            );
          }
      ),
    );
  }
}
