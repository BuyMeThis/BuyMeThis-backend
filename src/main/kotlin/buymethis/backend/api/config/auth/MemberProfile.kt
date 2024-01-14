package buymethis.backend.api.config.auth

import buymethis.backend.api.dao.memebr.MemberEntity

data class MemberProfile(
    var name: String,
    var email: String,
    var provider: String,
    var nickname: String? = null
) {
    fun toMember(): MemberEntity {
        return MemberEntity(
            name = name,
            email = email,
            provider = provider,
            nickname = nickname
        )
    }
}