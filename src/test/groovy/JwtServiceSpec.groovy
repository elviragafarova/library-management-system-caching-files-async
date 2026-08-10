import com.example.mslibrarymanagementsystem.security.JwtService
import spock.lang.Specification
import com.example.mslibrarymanagementsystem.entity.UserEntity
import com.example.mslibrarymanagementsystem.enums.Role
import io.jsonwebtoken.ExpiredJwtException

class JwtServiceSpec extends Specification {
    private JwtService jwtService

    def setup() {
        jwtService = new JwtService(
                "1234567890123456789012345678901234567890123456789012345678901234",
                3600000
        )
    }

    def "generate valid token test"() {

        given:
        def user = UserEntity.builder()
                .email("test@example.com")
                .role(Role.ROLE_USER)
                .build()

        when:
        String token = jwtService.generateToken(user)

        then:
        jwtService.extractEmail(token) == "test@example.com"
        jwtService.extractRole(token) == "ROLE_USER"
        !jwtService.isTokenExpired(token)
    }

    def "expired token test"() {

        given:
        def expiredJwtService = new JwtService(
                "1234567890123456789012345678901234567890123456789012345678901234",
                -1000
        )

        def user = UserEntity.builder()
                .email("test@example.com")
                .role(Role.ROLE_USER)
                .build()

        String token = expiredJwtService.generateToken(user)

        when:
        expiredJwtService.extractEmail(token)

        then:
        thrown(ExpiredJwtException)
    }
}