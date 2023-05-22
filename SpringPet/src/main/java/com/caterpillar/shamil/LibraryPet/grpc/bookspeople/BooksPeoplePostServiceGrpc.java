package com.caterpillar.shamil.LibraryPet.grpc.bookspeople;

import com.caterpillar.shamil.LibraryPet.entity.Books;
import com.caterpillar.shamil.LibraryPet.entity.BooksPeople;
import com.caterpillar.shamil.LibraryPet.entity.People;
import com.caterpillar.shamil.LibraryPet.repository.BooksPeopleRepository;
import com.caterpillar.shamil.proto.BooksPeopleOuterClass;
import com.caterpillar.shamil.proto.PostBPServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import static com.caterpillar.shamil.LibraryPet.grpc.bookspeople.BooksPeopleGetServiceGrpc.createBooksPeopleProto;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 08.05.2023
 */
@GRpcService
public class BooksPeoplePostServiceGrpc extends PostBPServiceGrpc.PostBPServiceImplBase {

    private final BooksPeopleRepository booksPeopleRepository;

    public BooksPeoplePostServiceGrpc(BooksPeopleRepository booksPeopleRepository) {
        this.booksPeopleRepository = booksPeopleRepository;
    }

    @Override
    public void createBooksPeople(BooksPeopleOuterClass.BooksPeopleRequest request, StreamObserver<BooksPeopleOuterClass.BooksPeople> responseObserver) {
        var book = new Books(request.getBook().getTitle(),request.getBook().getAuthor());
        var person = new People(request.getPerson().getLastName(),
                                request.getPerson().getFirstName(),
                                request.getPerson().getPatronymic());
        var booksPeople = new BooksPeople(book, person);
        booksPeopleRepository.save(booksPeople);
        var booksPeopleProto = createBooksPeopleProto(booksPeople);
        responseObserver.onNext(booksPeopleProto);
        responseObserver.onCompleted();
    }
}
