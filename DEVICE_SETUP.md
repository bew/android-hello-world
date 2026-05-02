# Device setup

## Wireless - pairing (first time only)

1. Enable **Wireless debugging**: Settings → Developer options → Wireless debugging
2. Tap **Pair device with pairing code** — note the IP:port and 6-digit code shown
3. On your machine:
   ```sh
   adb pair <ip>:<pairing-port>
   # Enter the 6-digit code when prompted
   ```

   > [!WARNING]
   > Note that the port for `adb pair` is different from the port for `adb connect` !

## Wireless - connect & install

> [!NOTE]
> The device must be paired first! See above.

1. In **Wireless debugging** settings page, note the `IP:port` on the main screen.
2. Connect:
   ```sh
   adb connect <ip>:<port>
   ```
3. Make sure the device appears in the list of devices: `adb devices`
4. Run `just install`

## USB - connect & install

1. Enable **USB debugging**: Settings → Developer options → USB debugging
2. Connect via USB and accept the prompt on your phone
3. Make sure the device appears in the list of devices: `adb devices`
4. Run `just install`

## Disable Play Protect popup

When installing the app, your phone may ask to send the app to Play Protect for scanning.

You can permanently disable this prompt (refusing it) using:
```sh
adb shell settings put global package_verifier_user_consent -1
```
