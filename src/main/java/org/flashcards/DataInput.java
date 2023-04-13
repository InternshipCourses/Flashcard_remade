package org.flashcards;

import java.io.Closeable;
import java.util.function.Supplier;

public interface DataInput<T> extends Supplier<T>, Closeable {
}
