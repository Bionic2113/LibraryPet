package com.caterpillar.shamil.LibraryPet.grpc.people;

import com.caterpillar.shamil.LibraryPet.repository.PeopleRepository;
import com.caterpillar.shamil.proto.Books;
import com.caterpillar.shamil.proto.DeletePersonServiceGrpc;
import com.caterpillar.shamil.proto.People;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.NoSuchElementException;

import static com.caterpillar.shamil.LibraryPet.grpc.people.PeopleGetServiceGrpc.createPersonProto;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 08.05.2023
 */
@GRpcService
public class PeopleDeleteServiceGrpc extends DeletePersonServiceGrpc.DeletePersonServiceImplBase {

    private final PeopleRepository peopleRepository;

    public PeopleDeleteServiceGrpc(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public void deletePerson(Books.Id request, StreamObserver<People.Person> responseObserver) {
        var person = peopleRepository.findById(request.getId());
        if(person.isEmpty()){
            responseObserver.onError(new NoSuchElementException("Человек не найден"));
        }
        else {
            var person_true  = person.orElseThrow();
            person_true.setActive(false);
            peopleRepository.save(person_true);
            responseObserver.onNext(createPersonProto(person_true));
        }
        responseObserver.onCompleted();
    }
}
