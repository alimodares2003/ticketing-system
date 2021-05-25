package com.balonet.ticketing.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import javax.persistence.*


@Entity
@Table(name = "answers")
class Answer {

    @Id
    @Column(
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(
        nullable = false,
        columnDefinition = "text"
    )
    var description: String? = null

    @Column
    var `operator`: Boolean? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "ticket_id",
        nullable = false
    )
    var ticket: Ticket? = null

    @CreationTimestamp
    var cdt: Timestamp? = null

    @UpdateTimestamp
    var udt: Timestamp? = null

}