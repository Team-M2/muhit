# muhit - Closest Point of Interest

<table>
<tr>
<td>
<img src="https://www.yuklio.com/f/zaryl-screenshot_20200624_164647_kodz.org.cevremdenevar.jpg" width="300">
</td>
<td>
<img src="https://www.yuklio.com/f/0SSQ4-screenshot_20200624_164655_kodz.org.cevremdenevar.jpg" width="300">
</td>
<td>
<img src="https://www.yuklio.com/f/oz0P7-screenshot_20200624_164738_kodz.org.cevremdenevar.jpg" width="300">
</td>
</tr>
</table>


## This application is a reference application prepared to introduce the use of Map Kit, Location Kit and Site Kit used for location based operations within the Huawei Mobile Services (HMS) ecosystem.


### Before Start

You need to agconnect-services.json for run this project correctly.

- If you don't have a Huawei Developer account check this document for create; https://developer.huawei.com/consumer/en/doc/start/10104
- Open your Android project and find Debug FingerPrint (SHA256) with follow this steps; View -> Tool Windows -> Gradle -> Tasks -> Android -> signingReport
- Login to Huawei Developer Console (https://developer.huawei.com/consumer/en/console)
- If you don't have any app check this document for create; https://developer.huawei.com/consumer/en/doc/distribution/app/agc-create_app
- Add SHA256 FingerPrint into your app with follow this steps on Huawei Console; My Apps -> Select App -> Project Settings
- Make enable necessary SDKs with follow this steps; My Apps -> Select App -> Project Settings -> Manage APIs
- For this project you have to set enable Location Kit, Map Kit and Site Kit
- Than go again Project Settings page and click "agconnect-services.json" button for download json file. 
- Move to json file in base "app" folder that under your android project. (https://developer.huawei.com/consumer/en/doc/development/HMS-Guides/69407812#h1-1577692046342)
- Go to app level gradle file and change application id of your android project. It must be same with app id on AppGallery console you defined.

### Application Promotion

- When you open the application, you will see a full screen map. This map is provided with the Huawei Map Kit.
- If you grant location permission, Huawei Location Kit provides instant location information.
- Your obtained location information is sent to Huawei Site Kit to reach the closest POIs to your location. (POI: Point of Interest)
- You choose the type of POI you want to see from the drop-down box on the screen, and thanks to the data provided by Huawei Site Kit, the nearest POI list is created.
- The information in this list is processed and added to the Huawei Map as a marker (pin).
- For example, when you select ATM from the list, you can quickly see the ATMs closest to you on the map.


### Things to Know
- Since the application is written entirely in HMS, you must have HMS Core installed on your device.
- For Android devices without HMS Core, you can download the latest version from this link; https://tik.to/9l6


### To-do
- Directions API
- ...


### Utilized resources
- https://medium.com/huawei-developers-tr/huawei-harita-servisi-581826f8e2a5
- https://medium.com/@mine1810/how-to-customize-marker-information-window-60b497ffdb83
- https://medium.com/@mine1810/how-to-get-places-with-using-hms-site-kit-562245b6c10e


### <a href="https://yadi.sk/d/xsx0CNeHu5bNkQ" target="_blank"> Click here </a> to download as apk

<table width="100%">
    <tbody style="float:left; width:100%;">
        <tr style="float:left; width:100%;">
            <td style="float:left; width:50%;">
                <a href="https://appgallery7.huawei.com/#/app/C102351799" target="_blank" style="float:right">
                    <img src="https://huaweimobileservices.com/wp-content/uploads/2020/02/Badge-White-800x240.png" width="250">
                </a>
            </td>
            <td style="float:left; width:50%; text-align:right;" align="right">
                <img src="https://www.yuklio.com/f/ym46M-qr-muhit-ag.png">
            </td>
        </tr>
    </tbody>
</table>
