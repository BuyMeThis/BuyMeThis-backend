package buymethis.backend.api.controller.oauth

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/oauth")
class OAuthController {

    @GetMapping("/loginInfo")
    fun oauthLoginInfo(authentication: Authentication): String {
        val oAuth2User = authentication.principal as OAuth2User
        val attributes = oAuth2User.attributes
        return attributes.toString()
    }
}