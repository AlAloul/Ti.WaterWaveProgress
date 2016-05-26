Ti.WaterWaveProgress
====================

This is a Titanium module that exposes the library of [Modificator](https://github.com/Modificator/water-wave-progress).com

![](https://github.com/Modificator/water-wave-progress/raw/master/screenshot/p2en.gif)

Usage
=====

~~~
var progressView = require('ti.appwerft.waterwaveprogress').createProgressView({
    maxProgress : 100,
    progressWidth:4,
    waterWaveColor: '#00ff00',
    waterWaveBgColor: '#00aa00',
    progress2WaterWidth : 10,
    fontSize : 22
});
progressView.setProgress(65);
progressView.setCrestCount(2.1);
progressView.setAmplitude(0.7);
~~~

More parameters methods you can find in sources.

