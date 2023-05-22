package com.caterpillar.shamil.LibraryPet.grpc.bookspeople;

import com.caterpillar.shamil.LibraryPet.entity.Books;
import com.caterpillar.shamil.LibraryPet.entity.People;
import com.caterpillar.shamil.LibraryPet.repository.BooksPeopleRepository;
import com.caterpillar.shamil.proto.BooksPeopleOuterClass;
import com.caterpillar.shamil.proto.UpdateBPServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.NoSuchElementException;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 08.05.2023
 */
@GRpcService
public class BooksPeopleUpdateServiceGrpc extends UpdateBPServiceGrpc.UpdateBPServiceImplBase {

    private final BooksPeopleRepository booksPeopleRepository;

    public BooksPeopleUpdateServiceGrpc(BooksPeopleRepository booksPeopleRepository) {
        this.booksPeopleRepository = booksPeopleRepository;
    }

    @Override
    public void updateBooksPeople(BooksPeopleOuterClass.BooksPeople request,
                                  StreamObserver<BooksPeopleOuterClass.BooksPeople> responseObserver) {
        var booksPeopleOptional = booksPeopleRepository.findById(request.getId());
        if(booksPeopleOptional.isEmpty()){
            responseObserver.onError(new NoSuchElementException("Запись не найдена"));
        }
        else{
            var booksPeople = booksPeopleOptional.orElseThrow();
            booksPeople.setBooks(new Books(request.getBook().getTitle(),
                                           request.getBook().getAuthor()));
            booksPeople.setPeoples(new People(request.getPerson().getLastName(),
                                              request.getPerson().getFirstName(),
                                              request.getPerson().getPatronymic()));
            booksPeopleRepository.save(booksPeople);
            responseObserver.onNext(request);
            responseObserver.onCompleted();
        }
    }
}
