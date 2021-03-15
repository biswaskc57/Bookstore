package com.example.Bookstore.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Bookstore.model.Book;
import com.example.Bookstore.model.BookRepository;
import com.example.Bookstore.model.CategoryRepository;

@Controller
public class BookController {

	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CategoryRepository crepository;
	

	
	
	
	
//Show all books
@RequestMapping(value = {"/", "/booklist"})
	public String bookList(Model model) {

		model.addAttribute("books", repository.findAll());
		return "bookList";
	}

//RESTful service to get all students
	@RequestMapping(value = "/books", method = RequestMethod.GET)
public @ResponseBody List<Book> bookListRest() {	
    return (List<Book>) repository.findAll();
}    

// RESTful service to get student by id
@RequestMapping(value="/book/{id}", method = RequestMethod.GET)
public @ResponseBody Optional<Book> findStudentRest(@PathVariable("id") Long bookId) {	
	return repository.findById(bookId);
} 


//Adding a book
@RequestMapping(value =  "/add")
public String addStudent(Model model) {

	model.addAttribute("book", new Book());
	model.addAttribute("categories", crepository.findAll());
	
	return "addBook";
}

//Saving a book
//@RequestMapping(value =  "/save", method = RequestMethod.POST)
@RequestMapping(value = "/save", method = RequestMethod.POST)
public String save(Book book){
repository.save(book);
return "redirect:booklist";
}

//Delete book
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
public String deleteStudent(@PathVariable("id") Long bookId, Model model) {
repository.deleteById(bookId);
return "redirect:../booklist";
}

//Edit book
@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
public String addStudent(@PathVariable("id") Long bookId, Model model){
model.addAttribute("book", repository.findById(bookId));
model.addAttribute("categories", crepository.findAll());
return "editBook";
}


}
 