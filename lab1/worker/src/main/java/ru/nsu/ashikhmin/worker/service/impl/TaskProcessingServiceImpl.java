package ru.nsu.ashikhmin.worker.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.paukov.combinatorics.CombinatoricsFactory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import org.springframework.stereotype.Service;
import ru.nsu.ashikhmin.worker.service.ManagerService;
import ru.nsu.ashikhmin.worker.service.TaskProcessingService;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class TaskProcessingServiceImpl implements TaskProcessingService {

    private final ManagerService managerService;
    private final ExecutorService executorService;

    private static final int THREADS_NUMBER = 10;

    public TaskProcessingServiceImpl(ManagerService managerService) {
        this.managerService = managerService;
        this.executorService = Executors.newFixedThreadPool(THREADS_NUMBER);
    }

    @Override
    public void processTask(CrackHashManagerRequest request) {
        executorService.execute(() -> crackHashCode(request));
    }

    private void crackHashCode(CrackHashManagerRequest request) {
        log.info("start processing task: {}", request.getRequestId());
        ICombinatoricsVector<String> vector = CombinatoricsFactory.createVector(request.getAlphabet().getSymbols());
        List<String> answers = new ArrayList<>();
        for (int i = 1; i <= request.getMaxLength(); i++) {
            Generator<String> gen = CombinatoricsFactory.createPermutationWithRepetitionGenerator(vector, i);
            for (var string : gen) {
                MessageDigest md5 = null;
                try {
                    md5 = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                String inputString = String.join("", string.getVector());
                String hash = (new HexBinaryAdapter()).marshal(md5.digest(inputString.getBytes()));
                if (request.getHash().equalsIgnoreCase(hash)) {
                    answers.add(String.join("", string.getVector()));
                    log.info("added answer : {}", String.join("", string.getVector()));
                }
            }
        }
        log.info("end processing task : {}", request.getRequestId());
        managerService.sendResponse(request.getRequestId(), request.getPartNumber(), answers);
    }
}
