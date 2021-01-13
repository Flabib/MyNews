package id.practice.mynews.core.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import org.ocpsoft.prettytime.PrettyTime
import java.net.URI
import java.net.URISyntaxException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Tools {
    companion object {
        private var vibrantLightColorList = arrayOf(
            ColorDrawable(Color.parseColor("#ffeead")),
            ColorDrawable(Color.parseColor("#93cfb3")),
            ColorDrawable(Color.parseColor("#fd7a7a")),
            ColorDrawable(Color.parseColor("#faca5f")),
            ColorDrawable(Color.parseColor("#1ba798")),
            ColorDrawable(Color.parseColor("#6aa9ae")),
            ColorDrawable(Color.parseColor("#ffbf27")),
            ColorDrawable(Color.parseColor("#d93947"))
        )

        val randomDrawableColor: ColorDrawable
            get() {
                val idx = Random().nextInt(vibrantLightColorList.size)
                return vibrantLightColorList[idx]
            }

        fun dateToTimeFormat(oldStringDate: String): String? {
            val p = PrettyTime(Locale(country))
            var isTime: String? = null
            try {
                val sdf = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'",
                    Locale.ENGLISH
                )
                val date = sdf.parse(oldStringDate)
                isTime = p.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return isTime
        }

        fun dateFormat(oldStringDate: String): String {
            val newDate: String
            val dateFormat = SimpleDateFormat("E, d MMM yyyy", Locale(country))
            newDate = try {
                val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale(country)).parse(
                    oldStringDate
                )
                dateFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                oldStringDate
            }
            return newDate
        }

        private val country: String
            get() {
                val locale = Locale.getDefault()
                val country = locale.country.toString()
                return country.toLowerCase(Locale.ROOT)
            }

        @Throws(URISyntaxException::class)
        fun getDomainName(url: String?): String {
            val uri = URI(url)
            val domain: String = uri.host
            return if (domain.startsWith("www.")) domain.substring(4) else domain
        }
    }
}