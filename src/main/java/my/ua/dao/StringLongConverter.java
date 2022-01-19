package my.ua.dao;

import my.ua.model.Topic;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
//класс конвертирует List String (id) приходяций из checkbox при выборе тем в List Long (id)
//обязательно дабовление th:field="*{topics}" в тег input type="checkbox"  !!!!!!!!
//и добавление вконфигурацию SpringConfig метода addFormatters()
public class StringLongConverter implements Converter<String, Topic> {
    private final NoteDAO noteDAO;

    public StringLongConverter(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    @Override
    public Topic convert(String source) {
        Long id = Long.parseLong(source);
        Topic topic = noteDAO.getTopicToId(id);
        return topic;
    }
}
