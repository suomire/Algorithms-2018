package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        // TODO
        @SuppressWarnings("unchecked")
        Node<T> deletedNode = find((T) o);
        T t = (T) o;
        if (find(t) == null || root == null || o == null) return false;
        else {
            Node<T> parent = root;
            Node<T> current = root;
            boolean isLeft = true;
            //находим родителя
            while (current.value != t) {
                parent = current;
                if (t.compareTo(parent.value) < 0) {
                    isLeft = true;
                    current = current.left;
                } else {
                    isLeft = false;
                    current = current.right;
                }
            }
            //current - deleted element
            //проверяем наличие потомков
            if (current.left == null && current.right == null) {
                if (isLeft) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            } else if (current.left == null) {
                if (isLeft) {
                    parent.left = current.right;
                } else {
                    parent.right = current.right;
                }
            } else {
                Node<T> current_child = current.left;
                while (current_child.right != null) {
                    current_child = current_child.right;
                }
                parent = current_child;
                if (current == current_child) {
                    current_child = current.right;
                    while (current_child.left != null) {
                        current_child = current_child.left;
                    }
                    parent = current_child;
                }
            }
            return true;
        }
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;
        private Node<T> next = findNext();

        private BinaryTreeIterator() {
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        private Node<T> findNext() {
            // TODO
            current = next;
            if (next == null) {
                next = firstNode();
                return next;
            }

            if (next.right != null) {
                next = next.right;
                while (next.left != null) next = next.left;
                return next;
            }

            while (findParent(next) != null) {
                if (findParent(next).left == next) {
                    next = findParent(next);
                    return next;
                }
                next = findParent(next);
            }

            return null;

        }

        public Node<T> minimum(Node<T> node) {
            if (root == null) throw new NoSuchElementException();
            Node<T> cur = node;
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur;
        }

        public Node<T> maximum(Node<T> node) {
            if (root == null) throw new NoSuchElementException();
            Node<T> cur = node;
            while (cur.right != null) {
                cur = cur.right;
            }
            return cur;
        }

        public Node<T> findParent(Node<T> node) {
            Node<T> parent = root;
            Node<T> current = root;
            while (current.value != node.value) {
                parent = current;
                if (node.value.compareTo(parent.value) < 0) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            return parent;
        }


        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            // TODO
            throw new NotImplementedError();
        }

    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    private Node<T> firstNode() {
        if (root == null) throw new NoSuchElementException();
        Node<T> result = root;
        while (result.left != null) {
            result = result.left;
        }
        return result;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
