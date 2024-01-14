package buymethis.backend.api.dao.memebr

import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "member")
@DynamicUpdate
class MemberEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "provider", nullable = false)
    val provider: String,

    @Column(name = "nickname", nullable = true, unique = true)
    val nickname: String?
) {
    fun update(name: String, email: String): MemberEntity {
        this.name = name
        this.email = email
        return this
    }

}