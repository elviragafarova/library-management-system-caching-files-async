import com.example.mslibrarymanagementsystem.dto.request.AuthorRequest
import com.example.mslibrarymanagementsystem.entity.AuthorEntity
import com.example.mslibrarymanagementsystem.repository.AuthorRepository
import com.example.mslibrarymanagementsystem.service.AuthorService
import com.example.mslibrarymanagementsystem.exceptions.AuthorNotFoundException
import spock.lang.Specification

class AuthorServiceSpec extends Specification {
    private AuthorService authorService
    private AuthorRepository authorRepository

    def setup() {
        authorRepository = Mock(AuthorRepository)
        authorService = new AuthorService(authorRepository)
    }

    def "create author test"() {
        given:
        def authorRequest = AuthorRequest.builder()
                .name("Agatha Christie")
                .build()

        when:
        authorService.createAuthor(authorRequest)

        then:
        1 * authorRepository.save({
            it.name == "Agatha Christie"
        })
    }

    def "get author by id test"() {
        given:
        def author = AuthorEntity.builder()
                .id(1L)
                .name("Agatha Christie")
                .build()

        authorRepository.findById(1L) >> Optional.of(author)

        when:
        def response = authorService.getAuthorById(1L)

        then:
        response.id == 1L
        response.name == "Agatha Christie"

        1 * authorRepository.findById(1L)
    }

    def "author not found exception test"() {
        given:
        authorRepository.findById(1L) >> Optional.empty()

        when:
        authorService.getAuthorById(1L)

        then:
        def ex = thrown(AuthorNotFoundException)
        ex.message == "Author not found with id: 1"
    }

    def "delete author test"() {
        given:
        def author = AuthorEntity.builder()
                .id(1L)
                .name("Agatha Christie")
                .build()

        authorRepository.findById(1L) >> Optional.of(author)

        when:
        authorService.deleteAuthor(1L)

        then:
        1 * authorRepository.delete(author)
    }

    def "delete author not found test"() {
        given:
        authorRepository.findById(1L) >> Optional.empty()

        when:
        authorService.deleteAuthor(1L)

        then:
        def ex = thrown(AuthorNotFoundException)
        ex.message == "Author not found with id: 1"
    }

    def "update author test"() {
        given:
        def request = AuthorRequest.builder()
                .name("Agatha Christie")
                .build()

        def author = AuthorEntity.builder()
                .id(1L)
                .name("William Shakespeare")
                .build()

        authorRepository.findById(1L) >> Optional.of(author)
        authorRepository.save(author) >> author

        when:
        def response = authorService.updateAuthor(1L, request)

        then:
        response.name == "Agatha Christie"
    }

    def "update author not found test"() {
        given:
        def request = AuthorRequest.builder()
                .name("Agatha Christie")
                .build()

        authorRepository.findById(1L) >> Optional.empty()

        when:
        authorService.updateAuthor(1L, request)

        then:
        def ex = thrown(AuthorNotFoundException)
        ex.message == "Author not found with id: 1"
    }
}