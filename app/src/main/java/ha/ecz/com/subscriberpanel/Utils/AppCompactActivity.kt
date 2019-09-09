package ha.ecz.com.subscriberpanel.Utils

import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.checkSelfPermissionCompat(permission: String) =
        ActivityCompat.checkSelfPermission(this, permission)

fun AppCompatActivity.shouldShowRequestPermissionRationaleCompat(permission: String) =
        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun AppCompatActivity.requestPermissionsCompat(permissionsArray: Array<String>,
                                               requestCode: Int) {
    ActivityCompat.requestPermissions(this, permissionsArray, requestCode)
}