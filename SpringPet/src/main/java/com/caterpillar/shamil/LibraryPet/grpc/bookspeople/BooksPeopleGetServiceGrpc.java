package com.caterpillar.shamil.LibraryPet.grpc.bookspeople;

import com.caterpillar.shamil.LibraryPet.entity.BooksPeople;
import com.caterpillar.shamil.LibraryPet.repository.BooksPeopleRepository;
import com.caterpillar.shamil.proto.Books;
import com.caterpillar.shamil.proto.BooksPeopleOuterClass;
import com.caterpillar.shamil.proto.GetBPServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.caterpillar.shamil.LibraryPet.grpc.books.BooksGetServiceGrpc.createBookProto;
import static com.caterpillar.shamil.LibraryPet.grpc.people.PeopleGetServiceGrpc.createPersonProto;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 08.05.2023
 */
@GRpcService
public class BooksPeopleGetServiceGrpc extends GetBPServiceGrpc.GetBPServiceImplBase {

    private final BooksPeopleRepository booksPeopleRepository;

    public BooksPeopleGetServiceGrpc(BooksPeopleRepository booksPeopleRepository) {
        this.booksPeopleRepository = booksPeopleRepository;
    }//Обычный репозиторий для бд

    @Override
    public void getAllBooksPeople(Empty request, StreamObserver<BooksPeopleOuterClass.ListBooksPeople> responseObserver) {
        var booksPeople = booksPeopleRepository.findByActiveTrue();
        List<BooksPeopleOuterClass.BooksPeople> booksPeopleList = new ArrayList<>();
        for (BooksPeople bp : booksPeople){
            booksPeopleList.add(createBooksPeopleProto(bp));
        }
        responseObserver.onNext(BooksPeopleOuterClass.ListBooksPeople.
                                                    newBuilder()
                                                    .addAllBookPerson(booksPeopleList)
                                                    .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getBooksPeopleById(Books.Id request, StreamObserver<BooksPeopleOuterClass.BooksPeople> responseObserver) {
       var booksPeople =  booksPeopleRepository.findById(request.getId());// Нашел по айди, тк
       if(booksPeople.isEmpty()){                                                              // оба интеджеры то ок
           responseObserver.onError(new NoSuchElementException("Запись не найдена"));
       }
       else { //тут пересобираюсь сущность из бд в нужную для грпс и передаю в ответ от сервера
           responseObserver.onNext(createBooksPeopleProto(booksPeople.orElseThrow()));
       }
       responseObserver.onCompleted();//заканчиваю ответ так сказать
    }
    // Метод для перевода табличной сущности в сущность grpc
    public static BooksPeopleOuterClass.BooksPeople createBooksPeopleProto(BooksPeople booksPeople){
        return BooksPeopleOuterClass.BooksPeople.newBuilder()
                .setBook(createBookProto(booksPeople.getBooks()))
                .setPerson(createPersonProto(booksPeople.getPeoples()))
                .build();
    }
}
