import android.app.Application
import android.os.Parcel
import android.os.Parcelable
import com.example.marsphotos.data.AppContainer
import com.example.marsphotos.data.DefaultAppContainer


class MarsPhotosApplication() : Application(), Parcelable {
    lateinit var container: AppContainer

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MarsPhotosApplication> {
        override fun createFromParcel(parcel: Parcel): MarsPhotosApplication {
            return MarsPhotosApplication(parcel)
        }

        override fun newArray(size: Int): Array<MarsPhotosApplication?> {
            return arrayOfNulls(size)
        }
    }
}