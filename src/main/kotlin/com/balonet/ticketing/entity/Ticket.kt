package com.balonet.ticketing.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import javax.persistence.*


@Entity
@Table(name = "tickets")
class Ticket {

    @Id
    @Column(
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    var title: String? = null

    @Column(
        nullable = false,
        columnDefinition = "text"
    )
    var description: String? = null

    @Column
    var status: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_id",
        nullable = false
    )
    var user: User? = null

    @CreationTimestamp
    var cdt: Timestamp? = null

    @UpdateTimestamp
    var udt: Timestamp? = null

}
