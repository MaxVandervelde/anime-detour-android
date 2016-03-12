App Permissions
===============

This application requires several permissions from the Android operating system.
The goal of this document is to track the purpose of each permission request.

Network & Storage
-----------------

The permissions `INTERNET`and `ACCESS_NETWORK_STATE` are required for fetching
event, guest, and other convention data from the website.

Run at Startup
--------------

The `RECEIVE_BOOT_COMPLETED` permission is required in order to re-schedule
notification alarms for panels when the device is rebooted. Android does not
persist these scheduled alarms when the device is powered off, so we must
do this at every boot.
