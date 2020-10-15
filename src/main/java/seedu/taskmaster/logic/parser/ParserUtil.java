package seedu.taskmaster.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.taskmaster.commons.core.index.Index;
import seedu.taskmaster.commons.util.StringUtil;
import seedu.taskmaster.logic.parser.exceptions.ParseException;
import seedu.taskmaster.model.attendance.AttendanceType;
import seedu.taskmaster.model.student.Email;
import seedu.taskmaster.model.student.Name;
import seedu.taskmaster.model.student.NusnetId;
import seedu.taskmaster.model.student.Telegram;
import seedu.taskmaster.model.tag.Tag;
import seedu.taskmaster.storage.StorageManager;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String telegramUsername} into a {@code Telegram}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code telegramUsername} is invalid.
     */
    public static Telegram parseTelegram(String telegramUsername) throws ParseException {
        requireNonNull(telegramUsername);
        String trimmedUsername = telegramUsername.trim();
        if (!Telegram.isValidTelegram(trimmedUsername)) {
            throw new ParseException(Telegram.MESSAGE_CONSTRAINTS);
        }
        return new Telegram(trimmedUsername);
    }

    /**
     * Parses a {@code String nusnetId} into an {@code NusnetId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nusnetId} is invalid.
     */
    public static NusnetId parseNusnetId(String nusnetId) throws ParseException {
        requireNonNull(nusnetId);
        String trimmedNusnetId = nusnetId.trim();
        if (!NusnetId.isValidNusnetId(trimmedNusnetId)) {
            throw new ParseException(NusnetId.MESSAGE_CONSTRAINTS);
        }
        return new NusnetId(trimmedNusnetId);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String attendanceType} into an {@code AttendanceType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code attendanceType} is invalid.
     */
    public static AttendanceType parseAttendanceType(String attendanceType) throws ParseException {
        requireNonNull(attendanceType);
        String trimmedAttendanceType = attendanceType.trim().toUpperCase();
        if (!AttendanceType.isValidAttendanceType(trimmedAttendanceType)) {
            throw new ParseException(AttendanceType.MESSAGE_CONSTRAINTS);
        }
        return AttendanceType.valueOf(trimmedAttendanceType);
    }

    /**
     * Parses a {@code String filename} into a filename.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code attendanceType} is invalid.
     */
    public static String parseFilename(String filename) throws ParseException {
        requireNonNull(filename);
        String trimmedFilename = filename.trim();
        if (!StorageManager.isValidFilename(filename)) {
            throw new ParseException(StorageManager.FILENAME_CONSTRAINTS);
        }
        return trimmedFilename;
    }
}
