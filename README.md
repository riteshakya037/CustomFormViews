[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=102)](https://github.com/ellerbrock/open-source-badge/)
[![Open Source Love](https://badges.frapsoft.com/os/mit/mit.svg?v=102)](https://github.com/ellerbrock/open-source-badge/)
[![Download](https://api.bintray.com/packages/riteshakya037/maven/customfieldviews/images/download.svg) ](https://bintray.com/riteshakya037/maven/customfieldviews/_latestVersion)


[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d55a8a3218d54c2fb2f58d5f7a784999)](https://www.codacy.com/app/riteshakya037/CustomFormViews?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=riteshakya037/CustomFormViews&amp;utm_campaign=Badge_Grade)
[![Code Climate](https://codeclimate.com/github/riteshakya037/CustomFormViews/badges/gpa.svg)](https://codeclimate.com/github/riteshakya037/CustomFormViews)
[![Issue Count](https://codeclimate.com/github/riteshakya037/CustomFormViews/badges/issue_count.svg)](https://codeclimate.com/github/riteshakya037/CustomFormViews)

Clean Form Views
==============================

<img src="screenshots/Header.png?raw=true" width="600" height="356">

A clean collection of views used for forms with excellent support for individual and combined validity.

Demo
===========================

[Clean Form Views (APK)](sample/sample-debug.apk)

Custom Text View
===========================

<img src="screenshots/CustomTextView.png?raw=true" width="600" height="356">
Clean TextViews with validation callbacks providing for a clean and easy form validator.


<img src="screenshots/CustomTextViewAsync.png?raw=true" width="600" height="356">
Also support for Asynchronous callbacks providing a loading animation while we can validate with server.

Custom Date View
===========================

<img src="screenshots/CustomDateView.png?raw=true" width="600" height="356">

Custom Spinner View
===========================

<img src="screenshots/CustomSpinnerView.png?raw=true" width="600" height="356">

Installation
===============================

Gradle

```
dependencies {
    compile 'com.ritesh:customfieldviews:1.1.0'
}
```

Feature
===========================

* Clean look with support for asynchronous callbacks 
* Easy to implement

Usage
===========================

Define 'app' namespace on root view in your layout

```xml
xmlns:app="http://schemas.android.com/apk/res-auto"
```

Include this library in your layout
```xml
<com.ritesh.customfieldviews.CustomTextView
                android:id="@+id/activity_main_text_email"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:inputType="textEmailAddress"
                app:editable="false"
                app:hint="Email" />
```

####  Supported Attributs

| XML Attribut        | View Supported in           | Description  |
| ------------- |:-------------:| ---------:|
| app:hint      | All     | Hint text that slides up as label. |
| app:text      | CustomTextView     | Prefilled text. |
| app:password      | CustomTextView     | Applies a monospace password field. |
| app:editable      | CustomTextView     | Set Editable flag for TextView. |
| android:inputType      | CustomTextView     | Sets teh input type of TextView. |
| app:title      | CustomDateView     | Sets the title of DatePicker. |
| android:entries      | CustomSpinnerView     | Set array resource to spinner. |

Please have a look at the sample project for further use cases.

Future Works
===========================

* Add support for customization
* Add tranisition animations

Libraries Used
===========================

* [Butter Knife](https://github.com/JakeWharton/butterknife) - View Injection Library
* [AVLoadingIndicatorView](https://github.com/81813780/AVLoadingIndicatorView) - Collection of nice loading animations for Android.


Contrbute
===========================

+   Fork the repo
+   create a branch
    ```
    git checkout -b my_branch
    ```
+   Add your changes
+   Commit your changes:
    ```
    git commit -am "Added some awesome stuff"
    ```
+   Push your branch:
    ```
    git push origin my_branch
    ```
+   Make a pull request to `develop` branch

## Changelog

+   **1.1.0** : Create library
+   **1.1.0** : Removed redundany while initializing form views.

Contact me
===========================

If you have a better idea or way on this project, please let me know, thanks :)

[Email](mailto:riteshakya037@gmail.com)

License
===========================

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
