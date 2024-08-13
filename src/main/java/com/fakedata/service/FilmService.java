package com.fakedata.service;

import com.fakedata.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public List<Integer> getRecommendationFilms(int user_id, int profile_id, int limit, int offset) {
        String string = filmRepository.findRecommendationByUserIDAndProfileID(user_id, profile_id);
        String[] ids = string.split(", ");
        int end = Math.min(offset + limit, ids.length);
        return Arrays.asList(ids).subList(offset, end - 1).stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    public List<Integer> getRelatedFilms(long film_id, long limit, long offset) {
        String genre = filmRepository.findById(film_id).get().getGenre();
        return filmRepository.findByGenreLimitOffset(genre, limit, offset);
    }
}
