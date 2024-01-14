package buymethis.backend.api.config.auth

import buymethis.backend.api.dao.memebr.MemberEntity
import buymethis.backend.api.repository.member.MemberRepository
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.stereotype.Service
import java.util.Collections
import java.util.LinkedHashMap

@Service
class OAuthService(
    private val memberRepository: MemberRepository
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId
        val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
        val attributes = oAuth2User.attributes

        val memberProfile = OAuthAttributes.extract(registrationId, attributes)
        memberProfile.provider = registrationId
        val member = saveOrUpdate(memberProfile)

        val customAttribute = customAttribute(attributes, userNameAttributeName, memberProfile, registrationId)

        return DefaultOAuth2User(
            Collections.singleton(SimpleGrantedAuthority("USER")),
            customAttribute,
            userNameAttributeName
        )
    }

    private fun customAttribute(attributes: Map<String, Any>, userNameAttributeName: String, memberProfile: MemberProfile, registrationId: String): Map<String, Any> {
        val customAttribute = LinkedHashMap<String, Any>()
        customAttribute[userNameAttributeName] = attributes[userNameAttributeName]!!
        customAttribute["provider"] = registrationId
        customAttribute["name"] = memberProfile.name
        customAttribute["email"] = memberProfile.email
        return customAttribute
    }

    private fun saveOrUpdate(memberProfile: MemberProfile): MemberEntity {
        return memberRepository.findByEmailAndProvider(memberProfile.email, memberProfile.provider)
            .map { it.update(memberProfile.name, memberProfile.email) }
            .orElse(memberProfile.toMember())
            .let { memberRepository.save(it) }
    }
}
