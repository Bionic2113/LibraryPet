package com.caterpillar.shamil.LibraryPet.grpc.people;

import com.caterpillar.shamil.LibraryPet.repository.PeopleRepository;
import com.caterpillar.shamil.proto.Books;
import com.caterpillar.shamil.proto.GetPersonServiceGrpc;
import com.caterpillar.shamil.proto.People;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 08.05.2023
 */
@GRpcService
public class PeopleGetServiceGrpc extends GetPersonServiceGrpc.GetPersonServiceImplBase {

    private final PeopleRepository peopleRepository;

    public PeopleGetServiceGrpc(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public void getAllPeople(Empty request, StreamObserver<People.ListPeople> responseObserver) {
        var people = peopleRepository.findByActiveTrue();
        List<People.Person> personList = new ArrayList<>();
        for (com.caterpillar.shamil.LibraryPet.entity.People person : people){
            personList.add(createPersonProto(person));
        }
        responseObserver.onNext(People.ListPeople.newBuilder().addAllPeople(personList).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getPersonById(Books.Id request, StreamObserver<People.Person> responseObserver) {
        var person = peopleRepository.findById(request.getId());
        if (person.isEmpty()){
            responseObserver.onError(new NoSuchElementException("Книга не найдена"));
        }
        else {
            responseObserver.onNext(createPersonProto(person.orElseThrow()));
        }
        responseObserver.onCompleted();
    }

    public static People.Person createPersonProto(com.caterpillar.shamil.LibraryPet.entity.People person){
        return People.Person.newBuilder()
                                .setLastName(person.getLastname())
                                .setFirstName(person.getLastname())
                                .setPatronymic(person.getPatronymic())
                                .setActive(person.isActive())
                                .build();
    }
}
