package com.example.study_mate.study.service;

import com.example.study_mate.study.domain.Study;


record ScoredStudy (
    Study study,
    int score
)
{ }
