package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.enums.BookStatusEnum;
import com.mobook.fastborrow.enums.ResultEnum;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.repository.BookMessageRepository;
import com.mobook.fastborrow.service.BookMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:09 2018\6\30 0030
 */
@Service
public class BookMessageServiceImpl implements BookMessageService {

    @Autowired
    private BookMessageRepository repository;

    @Override
    public BookMessage save(BookMessage bookMessage) {
        return repository.save(bookMessage);
    }

    @Override
    public Page<BookMessage> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public BookMessage findOne(String mobookId) {
        return repository.findById(mobookId).get();
    }

    @Override
    public List<BookMessage> findByWhereTag(String whereTag) {
        return repository.findByWhereTag(whereTag);
    }

    @Override
    public List<BookMessage> findByTagNum(Integer tagNum) {
        return repository.findByTagNum(tagNum);
    }

    @Override
    public List<BookMessage> findByRecNum(Integer recNum) {
        return repository.findByRecNum(recNum);
    }

    @Override
    public Page<BookMessage> findByStatusIsNotAAndTagNum(Integer status,Integer tagNum, Pageable pageable) {
        return repository.findByStatusIsNotAndTagNum(status,tagNum, pageable);
    }

    @Override
    public Page<BookMessage> findByStatusIsNotAndRecNumIs(Integer status, Pageable pageable) {
        return repository.findByStatusIsNotAndRecNumIs(status, 0,pageable);
    }

    @Override
    public Page<BookMessage> findByMobookIdAndBookName(String mobookId, String bookName, PageRequest pageable) {
        return repository.findByMobookIdIsLikeAndBookNameIsLike("%"+mobookId+"%"
                ,"%"+bookName+"%",pageable);
    }

    @Override
    public Page<BookMessage> findByBookName(String bookName, PageRequest pageable) {
        return repository.findByBookNameIsLike("%"+bookName+"%",pageable);
    }

    @Override
    public Page<BookMessage> findByMobookId(String mobookId, PageRequest pageable) {
        return repository.findByMobookIdIsLike("%"+mobookId+"%",pageable);
    }

    @Override
    public Page<BookMessage> findByMobookIdIsLikeAndStatus(String mobookId, Integer status, Pageable pageable) {
        return repository.findByMobookIdIsLikeAndStatus("%"+mobookId+"%",status,pageable);
    }

    @Override
    public Page<BookMessage> findByMobookIdIsLikeAndStatusIsNot(String mobookId, Integer status, Pageable pageable) {
        return repository.findByMobookIdIsLikeAndStatusIsNotAndRecNumIs("%"+mobookId+"%", status,0, pageable);
    }

    @Override
    public Page<BookMessage> findByBookNameIsLikeAndStatus(String bookName, Integer status, Pageable pageable) {
        return repository.findByBookNameIsLikeAndStatus("%"+bookName+"%", status, pageable);
    }

    @Override
    public Page<BookMessage> findByBookNameIsLikeAndStatusIsNot(String bookName, Integer status, Pageable pageable) {
        return repository.findByBookNameIsLikeAndStatusIsNotAndRecNumIs("%"+bookName+"%", status,0, pageable);
    }

    @Override
    public Page<BookMessage> findByMobookIdIsLikeAndBookNameIsLikeAndStatus(String mobookId, String bookName, Integer status, Pageable pageable) {
        return repository.findByMobookIdIsLikeAndBookNameIsLikeAndStatus("%"+mobookId+"%", "%"+bookName+"%", status, pageable);
    }

    @Override
    public Page<BookMessage> findByMobookIdIsLikeAndBookNameIsLikeAndStatusIsNot(String mobookId, String bookName, Integer status, Pageable pageable) {
        return repository.findByMobookIdIsLikeAndBookNameIsLikeAndStatusIsNotAndRecNumIs("%"+mobookId+"%", "%"+bookName+"%", status,0, pageable);
    }

    @Override
    public Page<BookMessage> findByStatus(Integer status, Pageable pageable) {
        return repository.findByStatus(status, pageable);
    }

    @Override
    public List<BookMessage> findByIsbn(String isbn) {
        return repository.findByIsbn(isbn);
    }

    @Override
    public List<BookMessage> findByStatus(Integer status) {
        return repository.findByStatus(status);
    }

    @Override
    public BookMessage onSale(String mobookId) {
        BookMessage bookMessage = repository.findById(mobookId).get();
        if (bookMessage == null){
            throw new FastBorrowException(ResultEnum.BOOK_NOT_EXIT);
        }
        if (bookMessage.getBookStatusEnum() == BookStatusEnum.UP){
            throw new FastBorrowException(ResultEnum.BOOK_STATUS_ERROR);
        }
        //更新
        bookMessage.setStatus(BookStatusEnum.UP.getCode());
        return repository.save(bookMessage);
    }

    @Override
    public BookMessage offSale(String mobookId) {
        BookMessage bookMessage = repository.findById(mobookId).get();
        if (bookMessage == null){
            throw new FastBorrowException(ResultEnum.BOOK_NOT_EXIT);
        }
        if (bookMessage.getBookStatusEnum() == BookStatusEnum.DOWN){
            throw new FastBorrowException(ResultEnum.BOOK_STATUS_ERROR);
        }
        //更新
        bookMessage.setStatus(BookStatusEnum.DOWN.getCode());
        return repository.save(bookMessage);
    }

}
