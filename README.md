# InterfaceInteraction

Influenced with [PhysicsLayout](https://github.com/Jawnnypoo/PhysicsLayout) project, we've implemented an interesting animation. Our library captures any interface (screen or view) and throws its UI elements over under the influence of gravity, so that one can move them from side to side obliquely. Gravity depends on device's [accelerometer data](https://developer.android.com/reference/android/hardware/SensorManager.html). Call *stop()* method to return all the UI elements to their original location. 

Another option is to apply _shake_ animation for your UI elements.

These animations can be easily used during the development of any application as an event activated after a specified user action.


### Gravity
<img src="https://cloud.githubusercontent.com/assets/1777595/26116081/8e029c0e-3a6a-11e7-9626-2753a9e31b83.gif" width="32%"> 

### Shake
<img src="https://user-images.githubusercontent.com/17047537/26974906-1fa4d884-4d26-11e7-9628-770d84ab0e7d.gif" width="32%">

## Example

### How does it work?

__Gravity:__

First, create an instance of *GravityControllerImpl*. Pass context instance and root *ViewGroup* as constructor parameters:

````kotlin
val gravityController = GravityControllerImpl(this, rootLayout)
````

To initiate gravity animation of all subviews (except ViewGroup instances) and then return back these UI elements simply call appropriate methods:

````kotlin
gravityController.start()
gravityController.stop()
````

__Shake:__

Choose what you want to shake: all activity or only view. Then you need to build your ShakeBuilder, call the method *shake* where the parameter is yours view and in the end call the method *build*.

````kotlin
val shaker = InterfaceInteractorImpl().shake(this).build()
````

In order to start animation you need to call the method *shakeMyActivity* or *shakeMyView*. To complete the animation call the method *stopAnimation*

````kotlin
shaker.shakeMyActivity()
shaker.shakeMyView()
shaker.stopAnimation()
````

## Usage

### Gradle

Add dependency in your `build.gradle` file:
````gradle
compile 'com.agilie:interface-interaction:1.0'
````

### Maven
Add rependency in your `.pom` file:
````xml
<dependency>
  <groupId>com.agilie</groupId>
  <artifactId>interface-interaction</artifactId>
  <version>1.0</version>
  <type>pom</type>
</dependency>
````

## Requirements

AGMobileGift works on Android API 19+

## Troubleshooting

Problems? Check the [Issues](https://github.com/agilie/AGMobileGift/issues) block
to find the solution or create an new issue that we will fix asap.


## Author

This library is open-sourced by [Agilie Team](https://www.agilie.com?utm_source=github&utm_medium=referral&utm_campaign=Git_Android_Kotlin&utm_term=InterfaceInteraction) <info@agilie.com>

## Contributors

- [Eugene Surkov](https://github.com/ukevgen)

## Contact us
If you have any questions, suggestions or just need a help with web or mobile development, please email us at<br/> <android@agilie.com><br/>
You can ask us anything from basic to complex questions. <br/>
We will continue publishing new open-source projects. Stay with us, more updates will follow!<br/>

## License

The [MIT](LICENSE.md) License (MIT) Copyright © 2017 [Agilie Team](https://www.agilie.com?utm_source=github&utm_medium=referral&utm_campaign=Git_Android_Kotlin&utm_term=InterfaceInteraction)

