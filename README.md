![](logo/logo.png)

#Mobile Image Pipeline

Mobile image pipeline aims at minimizing the burden to manage images in different densities when building a mobile application.

Mobile image pipeline allows to generate images into multiple resolutions and apply filters directly from an editable format (.svg) or images in high resolution (.png, .jpg) in order to be able to resize any asset for a pixel perfect rendering.

# Key ideas

- no configuration file per image, the filename holds the pipeline description
- 100% java, no third party app to install, no environment variable to set, multi-platform


# Getting started

### Pull the last version

	git clone https://github.com/macray/mobile-image-pipeline.git

### Adapt the configuration file

Edit the default configuration file **default.config.properties**

Android:

	source.android.dir=/where/your/raw/assets/are
	target.android.dir=/your/app/src/main/res

iOS:

	source.ios.dir=/where/your/raw/assets/are
	target.ios.dir=/your/app/ios/images/
	
### Examples

Let's say, you have an image in SVG and you want to display it with an height of 150px in the highest resolution.

Name your source file like this:

	my_icon-h150.svg

**Important note: the character "-" prefix any filter description and its not allowed in the base name.**

You also want your icon to be red, then rename your file:

	my_icon-h150-tint#FF0000.svg

then generate the image for each density

	java -jar <mip.jar> /path/to/default.config.properties
	
you should see my_icon.png in the folder defined as target.

###Batch filtering

You can define a global pre or post filters on a whole folder.

In the case you'd like to resize a bunch of images to 100x100 and apply a black and white filter on them. You need to put the images in a folder ( i.e *batch_folder*).

Make a configuration file dedicated to this folder (copy default.configuration.properties to batch-folder-conig.properties) 
Then update those 2 lines:

	filters.global.pre=w100h100-bw
	source.android.dir=/your/resources/path/batch_folder

	java -jar {jar file name} batch-folder-config.properties


# More details
##Multiple configurations

You can add as many configuration you'd like

	java -jar {jar file name} icons.properties thumbnails.properties other.properties



## Available filters
Filter name  | Description | Example
----------------|-----------------|-------------
black & white |  Black & White effect | bw
Auto-crop | Crop the image until a line with at least 2 colors is found | autocrop
Fill Square | Make the image square and fill the added content with the border color | fillsquare
Horizontal Flip | Flip the image horizontally | hflip
Vertical Flip | Flip the image vertically | vflip
Tint | Tint the image the specified color | tint#FF0000 (mixValue tint#FF0000,0.3)
Resize | Define the target size in the default density (see config) | h100 (will resize the image with a height of 100px and compute the width for keeping the aspect ration) , h120w200 will resize to 120 px height and 200 px width

## Marker
A marker use the same syntax as a filter but it add semantic to the image.

Marker name | Description | Example
------------|-------------|-----
icon | (**Android only**) Mark the image as a launch icon. A launcher icon need to be generated in an upper density than traditional resources | ic_launcher-icon.svg

## Configuration file

### Generic

Name | Description | Default value
-----|-------------|--------------
source.extensions.allowed | only the file with allowed type will be processed | *png,jpg,svg*
source.recursive-load | load sub-folders too ? | *true*
filters.global.pre | filter to apply on every images before those explicitly requested | *none*
filters.global.post | filter to apply on every images after those explicitly requested  | *none*

### Android specific
	
Name | Description | Default value
-----|-------------|--------------
target.android.densities.default | Default target density | *mandatory*
target.android.dir | | *mandatory*
source.android.dir | | *mandatory*
source.android.density | the source density is used to tell MIP wha | *mandatory*

you are only allowed to reduce the of an image, trying to extend an image an exception will be thrown

### iOS specific

Name | Description | Default value
-----|-------------|--------------
source.ios.dir | folder where your source images can be found | mandatory
target.ios.dir | destination folder | mandatory
target.ios.densities | select which densities you'd like to have generated | x1,x2,x3


## Common mistake

Common mistake "_" instead of "-", vice and versa

## Feedback & push request are welcome

Please let me know if you think the documentation is unclear.

