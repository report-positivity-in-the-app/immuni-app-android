package org.immuni.android.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.immuni.android.models.survey.Survey
import org.immuni.android.models.survey.raw.RawSurvey

@JsonClass(generateAdapter = true)
class ImmuniSettings(
    @field:Json(name = "__tos_version__") var tosVersion: String? = null,
    @field:Json(name = "__privacy_notice_version__") var privacyVersion: String? = null,
    @field:Json(name = "min_build_version") var minBuildVersion: Int = 0,
    @field:Json(name = "development_devices") val developmentDevices: List<String> = listOf(),
    @field:Json(name = "reminder_notification_title") val reminderNotificationTitle: String = "Compila il diario",
    @field:Json(name = "reminder_notification_message") val reminderNotificationMessage: String = "Ricordati di compilare il diario clinico di oggi",
    @field:Json(name = "privacy_url") val privacyPolicyUrl: String? = null,
    @field:Json(name = "tos_url") val termsOfServiceUrl: String? = null,
    @field:Json(name = "faq_url") val faqUrl: String? = null,
    @field:Json(name = "survey_json") val rawSurvey: RawSurvey? = null,
    @field:Json(name = "disable_survey_back") val disableSurveyBack: Boolean = false,
    @field:Json(name = "user_data_retention_days") val userDataRetentionDays: Int? = null,
    @field:Json(name = "app_update_url") val appUpdateUrl: String? = null,
    @field:Json(name = "support_email") val supportEmail: String? = null,
    @field:Json(name = "delete_data_email") val deleteDataEmail: String? = null,
    @field:Json(name = "recover_data_email") val recoverDataEmail: String? = null,
    @field:Json(name = "ble_disable_all") val bleDisableAll: Boolean = false,
    @field:Json(name = "ble_timeout_seconds") val bleTimeoutSeconds: Int = 180,
    @field:Json(name = "ble_slots_per_contact_record") val bleSlotsPerContactRecord: Int = 256,
    @field:Json(name = "ble_advertise_mode") val bleAdvertiseMode: AdvertiseMode = AdvertiseMode.ADVERTISE_MODE_LOW_POWER,
    @field:Json(name = "ble_scan_mode") val bleScanMode: ScanMode = ScanMode.SCAN_MODE_BALANCED,
    @field:Json(name = "ble_tx_power_level") val bleTxPowerLevel: TxPowerLevel = TxPowerLevel.ADVERTISE_TX_POWER_ULTRA_LOW
) {
    @Transient private var _survey: Survey? = null
    val survey: Survey?
        get() {
            if (_survey == null) _survey = rawSurvey?.survey()
            return _survey
        }
}
