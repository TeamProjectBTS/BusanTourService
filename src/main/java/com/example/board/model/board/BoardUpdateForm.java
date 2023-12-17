package com.example.board.model.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BoardUpdateForm {
    private Long board_id;
    @NotBlank
    private String b_title;
    @NotBlank
    private String b_contents;
    private String member_id;
    private String nickname;
    private Long b_view_count;
    private Long b_like_count;
    private Long b_com_count;
    private LocalDate wr_date;
    private boolean fileRemoved;

}
