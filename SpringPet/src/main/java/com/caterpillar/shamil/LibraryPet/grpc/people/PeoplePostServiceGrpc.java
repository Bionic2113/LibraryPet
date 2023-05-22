package com.caterpillar.shamil.LibraryPet.grpc.people;

import com.caterpillar.shamil.LibraryPet.repository.PeopleRepository;
import com.caterpillar.shamil.proto.People;
import com.caterpillar.shamil.proto.PostPersonServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import static com.caterpillar.shamil.LibraryPet.grpc.people.PeopleGetServiceGrpc.createPersonProto;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 08.05.2023
 */
@GRpcService
public class PeoplePostServiceGrpc extends PostPersonServiceGrpc.PostPersonServiceImplBase {

    private final PeopleRepository peopleRepository;

    public PeoplePostServiceGrpc(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public void createPerson(People.PeopleRequest request, StreamObserver<People.Person> responseObserver) {
        var person = new com.caterpillar.shamil.LibraryPet.entity.People(
                                            request.getLastName(),
                                            request.getFirstName(),
                                            request.getPatronymic());
        peopleRepository.save(person);
        responseObserver.onNext(People.Person.newBuilder(createPersonProto(person)).build());
        responseObserver.onCompleted();
    }
}
