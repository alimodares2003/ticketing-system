package com.balonet.ticketing.entity

import com.balonet.ticketing.model.Role
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "users")
class User {
    @Id
    @Column(
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(
        nullable = false,
        unique = true
    )
    var email: String? = null

    @Column(nullable = false)
    var password: String? = null

    @Column
    var firstname: String? = null

    @Column
    var lastname: String? = null

    @Column
    var role: String? = Role.USER.name

    @OneToMany(mappedBy = "user")
    var userTickets: MutableSet<Ticket>? = null

    @CreationTimestamp
    var cdt: Timestamp? = null

    @UpdateTimestamp
    var udt: Timestamp? = null
}
