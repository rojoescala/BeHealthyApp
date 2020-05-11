package escalante.roberto.behealthy.utilies

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import escalante.roberto.behealthy.R

class ReminderBroadcaster: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        var builder:NotificationCompat.Builder=NotificationCompat.Builder(p0!!,"tomaAgua")
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setContentTitle("No olvides tomar agua")
        builder.setContentText("Recuerda tomar tus 8 vasos de agua")
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT)

        var nofManager:NotificationManagerCompat= NotificationManagerCompat.from(p0)
        nofManager.notify(200,builder.build())
    }
}