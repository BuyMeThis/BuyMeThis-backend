package buymethis.backend.api.repository.member

import buymethis.backend.api.dao.memebr.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MemberRepository : JpaRepository<MemberEntity, Long> {
    //이미 생성된 사용자인지 처음 가입하는 사용자인지 판단
    fun findByEmailAndProvider(email: String, provider: String): Optional<MemberEntity>
}