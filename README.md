
Information of this project:
* Code Language: Kotlin.
* Architecture: MVVM.

----------------------------------------------   

Gradle config:
* Package name: com.android.developmentchallenge
* MinSdkVersion: 28
* TargetSdkVersion: 32
* VersionCode: 1
* VersionName: 1.0

----------------------------------------------   

Structure of project:

Project: name of project.

-- app: Contains application-related processing files.

-- base: All abstract base class for inherits.

---- adapter: Adapter base, dev can extent to this for build new adapter.

---- model: Model base, Constructed classes for inheritance.

---- view: View base, Constructed classes for inheritance.

---- viewmodel: View Model base, Constructed classes for inheritance.

-- const: All const value, this will be application level.

-- di: Dependency injection (koin 2 refer: https://insert-koin.io/)

---- apiModule: All di of api.

---- dbModule: All di of database, room.

---- singletonModule: All singleton created by koin.

---- viewModelModule: All di of view model.

-- model: Model base, classes for inheritance.

-- network: All interface class for implement service.

---- response: Response model of API result.

---- service: interface of Service.

-- view: All class for view: activity, fragment, dialog,... (group by screen)

-- room: All class for implement room data base.

---- dao: All DAO of room.

-- singleton: All singleton class for data.

-- utils: All class for handle data, event, custom view, dialog, ext, helper, interfaces,...

---- bindings: All data binding adapter.

---- controls: All control for view.

---- customview: All custom view.

---- ext: extension for any class and object.

---- helper: helper object, handle data and control dialog and any class specially stick to the view.

---- interfaces: All interface.

---- dialog: All dialog custom for application.

-- view: All fragment, activity and view model. View and view model need on one folder when them have same name or function

-- viewModel: All view model for view.

----------------------------------------------   

The libraries have already been added:
* Kotlin: 1.7.0
* Kotlin-reflect: 1.6.10
* App combat: 1.4.1
* Material component: 1.5.0
* Recycler View: 1.2.1
* Constrain layout: 2.1.3
* Lifecycle data: 2.4.1
* Timber: 5.0.1
* Koin: '.2.0-beta-1
* Rxjava3: 3.1.3
* Retrofit2: 2.9.0
* Okhttp: 5.0.0-alpha.4
* Legacy Support: 1.0.0
* Room: 2.4.1
* Circle Imageview: 3.1.0

----------------------------------------------       

Build information:
* Flavors (config build output):
    - Develop: for develop environment.
        + Package name is: com.android.developmentchallenge.develop
        + App name: DevChallenge-Dev
        + Url: (Update when released)
    - Production: for production environment.
        + Package name is: com.android.developmentchallenge
        + App name: DevChallenge
        + Url: (Update when released)

* Signing config
    - Type Debug: (Update when released)
    - Type Release: (Update when released)

----------------------------------------------     

Build and run:
* IDE: Android studio: 2021.1.1 path 1
* Gradle version: 7.2.1
* Step build:
    - Open project with android studio.
    - Open File -> Sync project with gradle file.
    - Select flavor and type on Build variants.
    - Run this application or build apk file.
