package pl.khuzzuk.battles.model

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class DamageOrderSpec extends Specification {
    def "should be equal"() {
        given:
        DamageOrder order = new DamageOrder(Reach.MISSILE, Speed.MEDIUM)
        DamageOrder otherOrder = new DamageOrder(Reach.MISSILE, Speed.MEDIUM)

        when:
        int compare = order <=> otherOrder
        int transitiveCompare = otherOrder <=> order

        then:
        compare == 0
        transitiveCompare == 0
    }

    def "greater"() {
        given:
        DamageOrder order = new DamageOrder(distance, speed)
        DamageOrder otherOrder = new DamageOrder(otherDistance, otherSpeed)

        when:
        int compare = order <=> otherOrder
        int transitiveCompare = otherOrder <=> order

        then:
        compare > 0
        transitiveCompare < 0

        where:
        distance      | otherDistance    | speed        | otherSpeed
        Reach.SPEAR   | Reach.SHORT      | Speed.SLOW   | Speed.SLOW
        Reach.SPEAR   | Reach.SPEAR      | Speed.MEDIUM | Speed.SLOW
        Reach.MISSILE | Reach.LONG_SPEAR | Speed.SLOW   | Speed.FAST
    }
}
