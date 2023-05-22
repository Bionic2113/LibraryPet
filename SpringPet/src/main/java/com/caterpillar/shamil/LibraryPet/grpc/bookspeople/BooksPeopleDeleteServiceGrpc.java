package com.caterpillar.shamil.LibraryPet.grpc.bookspeople;

import com.caterpillar.shamil.LibraryPet.repository.BooksPeopleRepository;
import com.caterpillar.shamil.proto.Books;
import com.caterpillar.shamil.proto.BooksPeopleOuterClass;
import com.caterpillar.shamil.proto.DeleteBPServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.NoSuchElementException;

import static com.caterpillar.shamil.LibraryPet.grpc.bookspeople.BooksPeopleGetServiceGrpc.createBooksPeopleProto;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 08.05.2023
 */
@GRpcService
public class BooksPeopleDeleteServiceGrpc extends DeleteBPServiceGrpc.DeleteBPServiceImplBase {

    private final BooksPeopleRepository booksPeopleRepository;

    public BooksPeopleDeleteServiceGrpc(BooksPeopleRepository booksPeopleRepository) {
        this.booksPeopleRepository = booksPeopleRepository;
    }

    @Override
    public void deleteBooksPeople(Books.Id request, StreamObserver<BooksPeopleOuterClass.BooksPeople> responseObserver) {
        var booksPeople = booksPeopleRepository.findById(request.getId());
        if(booksPeople.isEmpty()){
            responseObserver.onError(new NoSuchElementException("Запись не найдена"));
        }
        else {
            var booksPeople_true = booksPeople.orElseThrow();
            booksPeople_true.setActive(false);
            booksPeopleRepository.save(booksPeople_true);
            responseObserver.onNext(createBooksPeopleProto(booksPeople_true));
        }
        responseObserver.onCompleted();
    }
}
