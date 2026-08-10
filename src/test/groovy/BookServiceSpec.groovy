import com.example.mslibrarymanagementsystem.Application
import com.example.mslibrarymanagementsystem.dto.request.LoanRequest
import com.example.mslibrarymanagementsystem.entity.AuthorEntity
import com.example.mslibrarymanagementsystem.entity.BookEntity
import com.example.mslibrarymanagementsystem.entity.MemberEntity
import com.example.mslibrarymanagementsystem.repository.AuthorRepository
import com.example.mslibrarymanagementsystem.repository.BookRepository
import com.example.mslibrarymanagementsystem.repository.LoanRepository
import com.example.mslibrarymanagementsystem.repository.MemberRepository
import com.example.mslibrarymanagementsystem.service.BookService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
class BookServiceSpec extends Specification {
    @Autowired
    BookService bookService

    @Autowired
    LoanRepository loanRepository

    @Autowired
    BookRepository bookRepository

    @Autowired
    MemberRepository memberRepository

    @Autowired
    AuthorRepository authorRepository

    def "borrowBook should rollback transaction when loan creation fails"() {

        given:
        def uniqueId = System.currentTimeMillis()

        def author = authorRepository.save(
                AuthorEntity.builder()
                        .name("Author ${uniqueId}")
                        .build()
        )

        def member = memberRepository.save(
                MemberEntity.builder()
                        .firstName("Test")
                        .lastName("Member")
                        .email("test${uniqueId}@gmail.com")
                        .build()
        )

        def book = bookRepository.save(
                BookEntity.builder()
                        .isbn("978${uniqueId}")
                        .title("Test Book")
                        .genre("Novel")
                        .publishedYear(2026)
                        .isAvailable(true)
                        .author(author)
                        .build()
        )

        def loanCountBefore = loanRepository.count()

        def request = LoanRequest.builder()
                .memberId(member.id)
                .bookId(book.id)
                .loanDate(LocalDate.now())
                .dueDate(null)      // qəsdən DB constraint pozulur
                .build()

        when:
        bookService.borrowBook(book.id, member.id, request)

        then:
        thrown(DataIntegrityViolationException)

        and:
        loanRepository.count() == loanCountBefore

        def rolledBackBook = bookRepository.findById(book.id).orElseThrow()

        rolledBackBook.isAvailable()
        rolledBackBook.borrowedBy == null
    }
}