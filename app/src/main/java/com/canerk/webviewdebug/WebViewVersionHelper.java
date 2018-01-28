/*
 * Copyright (c) 2018 Caner Kamburoglu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.canerk.webviewdebug;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.webkit.WebSettings;

class WebViewVersionHelper {
    private static final String ANDROID_WEBVIEW_PACKAGE_NAME = "com.google.android.webview";
    private static final String CHROME_STABLE_PACKAGE_NAME = "com.android.chrome";
    private static final String CHROME_BETA_PACKAGE_NAME = "com.chrome.beta";
    private static final String CHROME_DEV_PACKAGE_NAME = "com.chrome.dev";
    private static final String CHROME_CANARY_PACKAGE_NAME = "com.chrome.canary";
    private static final String NOT_INSTALLED = "Not installed";

    static String getOutputText(Context context, WebSettings webViewSettings) {
        return "WebView user agent: " + webViewSettings.getUserAgentString() + "\n\n" +
                "System WebView version: " + getAndroidSystemWebViewVersion(context) + "\n" +
                "Chrome Stable version: " + getChromeStableVersion(context) + "\n" +
                "Chrome Beta version: " + getChromeBetaVersion(context) + "\n" +
                "Chrome Dev version: " + getChromeDevVersion(context) + "\n" +
                "Chrome Canary version: " + getChromeCanaryVersion(context) + "\n\n" +
                "WebView implementation is: " + getPossibleWebViewImplementationName(context, webViewSettings);
    }

    private static String getPossibleWebViewImplementationName(Context context, WebSettings webViewSettings) {
        String webViewUserAgent = webViewSettings.getUserAgentString();

        if (webViewUserAgent.contains(getAndroidSystemWebViewVersion(context))) {
            return "Android System WebView";
        } else if (webViewUserAgent.contains(getChromeStableVersion(context))) {
            return "Chrome Stable";
        } else if (webViewUserAgent.contains(getChromeBetaVersion(context))) {
            return "Chrome Beta";
        } else if (webViewUserAgent.contains(getChromeDevVersion(context))) {
            return "Chrome Dev";
        } else if (webViewUserAgent.contains(getChromeCanaryVersion(context))) {
            return "Chrome Canary";
        } else {
            return "Unknown";
        }
    }

    private static String getAndroidSystemWebViewVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(ANDROID_WEBVIEW_PACKAGE_NAME, 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return NOT_INSTALLED;
        }
    }

    private static String getChromeStableVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(CHROME_STABLE_PACKAGE_NAME, 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return NOT_INSTALLED;
        }
    }

    private static String getChromeBetaVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(CHROME_BETA_PACKAGE_NAME, 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return NOT_INSTALLED;
        }
    }

    private static String getChromeDevVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(CHROME_DEV_PACKAGE_NAME, 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return NOT_INSTALLED;
        }
    }

    private static String getChromeCanaryVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(CHROME_CANARY_PACKAGE_NAME, 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return NOT_INSTALLED;
        }
    }

}
