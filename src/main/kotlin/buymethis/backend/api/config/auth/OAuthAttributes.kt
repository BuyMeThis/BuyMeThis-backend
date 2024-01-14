package buymethis.backend.api.config.auth

enum class OAuthAttributes(
    private val registrationId: String,
    private val extractor: (Map<String, Any>) -> MemberProfile
) {
//    KAKAO("kakao",
//        { attributes ->
//            val kakaoAccount = attributes["kakao_account"] as Map<String, Any>
//            val kakaoProfile = kakaoAccount["profile"] as Map<String, Any>
//            MemberProfile(
//                name = kakaoProfile["nickname"] as String,
//                email = kakaoProfile["email"] as String,
//                provider = "kakao"
//            )
//        }),

    KAKAO("kakao",
        { attributes ->
            val kakaoAccount = attributes["kakao_account"] as Map<String, Any>
            val kakaoProfile = kakaoAccount["profile"] as Map<String, Any>
            MemberProfile(
                name = kakaoProfile["nickname"] as String,
                email = kakaoProfile["email"] as String,
                provider = "kakao"
            )
    });

    companion object {
        fun extract(registrationId: String, attributes: Map<String, Any>): MemberProfile {
            return values()
                .firstOrNull { it.registrationId == registrationId }
                ?.extractor?.invoke(attributes)
                ?: throw IllegalArgumentException("Unsupported registration ID: $registrationId")
        }
    }

}
